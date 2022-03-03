package ca.cgjennings.ui.textedit;

import ca.cgjennings.apps.arkham.StrangeEons;
import static ca.cgjennings.apps.arkham.editors.NavigationPoint.*;
import ca.cgjennings.apps.arkham.plugins.typescript.CodeAction;
import ca.cgjennings.apps.arkham.plugins.typescript.CompilationFactory;
import ca.cgjennings.apps.arkham.plugins.typescript.CompilationRoot;
import ca.cgjennings.apps.arkham.plugins.typescript.CompletionInfo;
import ca.cgjennings.apps.arkham.plugins.typescript.Diagnostic;
import ca.cgjennings.apps.arkham.plugins.typescript.FileTextChanges;
import ca.cgjennings.apps.arkham.plugins.typescript.TSLanguageServices;
import ca.cgjennings.text.MarkdownTransformer;
import ca.cgjennings.ui.IconProvider;
import java.awt.EventQueue;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Segment;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.CompletionProviderBase;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;
import org.fife.ui.rtextarea.Gutter;

/**
 * Code support for the TypeScript language.
 *
 * @author Chris Jennings <https://cgjennings.ca/contact>
 * @since 3.4
 */
public class TypeScriptCodeSupport extends DefaultCodeSupport {

    @Override
    public void install(CodeEditorBase editor) {
        super.install(editor);
        this.editor = editor;

        // if the TS engine is not already running, start it up now
        // in a background thread since it is likely to be used soon
        TSLanguageServices.getShared();
        EventQueue.invokeLater(() -> {
            // don't immediately create a root, delay a moment in case a file
            // is set just after; this prevents superfluous creation of
            // a potentially expensive compilation root
            if (root == null) {
                fileChanged(file);
                if (root != null) {
                    ac = new AutoCompletion(new TSCompletionProvider());
                    ac.setListCellRenderer(new CompletionRenderer(editor));
                    ac.setShowDescWindow(true);
                    ac.setAutoCompleteEnabled(true);
                    ac.setAutoActivationEnabled(true);
                    ac.setAutoActivationDelay(800);
                    ac.setAutoCompleteSingleChoices(true);
                    ac.install(editor.getTextArea());
                }
            }
        });
        editor.getTextArea().addParser(new TSParser(editor));
    }

    @Override
    public void uninstall(CodeEditorBase editor) {
        super.uninstall(editor);
        if (ac != null) {
            ac.uninstall();
            ac = null;
        }
    }

    @Override
    public Formatter createFormatter() {
        return new ScriptedFormatter("beautify-js.js", "js_beautify");
    }

    @Override
    public void fileChanged(File file) {
        this.file = file;
        root = CompilationFactory.forFile(file);
        // TODO set identifier for root
        identifier = "index.ts";
    }

    private CodeEditorBase editor;
    private File file;
    private String identifier;
    private CompilationRoot root;
    private AutoCompletion ac;

    private class TSParser extends AbstractParser {

        private final CodeEditorBase editor;
        private final DefaultParseResult result = new DefaultParseResult(this);

        public TSParser(CodeEditorBase editor) {
            this.editor = editor;
        }

        @Override
        public ParseResult parse(RSyntaxDocument rsd, String string) {
            try {
                final long start = System.currentTimeMillis();
                result.setError(null);
                result.clearNotices();

                Gutter gutter = editor.getScrollPane().getGutter();
                gutter.removeAllTrackingIcons();

                if (root != null) {
                    root.add(identifier, rsd.getText(0, rsd.getLength()));
                    List<Diagnostic> diagnostics = root.getDiagnostics(identifier, true, true);
                    for (Diagnostic d : diagnostics) {
                        if (d.hasLocation()) {
                            DefaultParserNotice notice = new DefaultParserNotice(
                                    this, d.message, d.line, d.offset, d.length
                            );
                            notice.setLevel(d.isWarning ? ParserNotice.Level.WARNING : ParserNotice.Level.ERROR);
                            gutter.addLineTrackingIcon(d.line, d.isWarning ? CodeEditorBase.ICON_WARNING : CodeEditorBase.ICON_ERROR, d.message);
                            result.addNotice(notice);
                        }
                    }
                }
                result.setParseTime(System.currentTimeMillis() - start);
            } catch (BadLocationException ble) {
                StrangeEons.log.log(Level.WARNING, ble, null);
                result.setError(ble);
            }
            return result;
        }
    }

    private class TSCompletionProvider extends CompletionProviderBase {

        public TSCompletionProvider() {
            setAutoActivationRules(true, ".");
        }

        public List<Completion> getCompletions(JTextComponent comp) {
            return getCompletionsImpl(comp);
        }

        @Override
        protected List<Completion> getCompletionsImpl(JTextComponent jtc) {
            if (root == null || identifier == null) {
                return Collections.emptyList();
            }
            root.add(identifier, editor.getText());
            
            final String prefix = getAlreadyEnteredText(jtc);
            
            final int caret = jtc.getCaretPosition();
            CompletionInfo info = root.getCodeCompletions(identifier, caret);
            if (info == null) {
                return Collections.emptyList();
            }
            List<Completion> results = new ArrayList<>(info.entries.size());
            for (CompletionInfo.Entry entry : info.entries) {
                if (entry.getTextToInsert().startsWith(prefix)) {
                    TSCompletion tc = createTSCompletion(entry, this, identifier, caret, prefix);
                    if (tc != null) {
                        results.add(tc);
                    }
                }
            }
            return results;
        }

        @Override
        public String getAlreadyEnteredText(JTextComponent comp) {
            Document doc = comp.getDocument();

            int dot = comp.getCaretPosition();
            Element root = doc.getDefaultRootElement();
            int index = root.getElementIndex(dot);
            Element elem = root.getElement(index);
            int start = elem.getStartOffset();
            int len = dot - start;
            try {
                doc.getText(start, len, seg);
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                return EMPTY_STRING;
            }

            int segEnd = seg.offset + len;
            start = segEnd - 1;
            while (start >= seg.offset && isValidChar(seg.array[start])) {
                start--;
            }
            start++;

            len = segEnd - start;
            return len == 0 ? EMPTY_STRING : new String(seg.array, start, len);

        }
        private Segment seg = new Segment();

        private boolean isValidChar(char ch) {
            return Character.isJavaIdentifierPart(ch);
        }

        @Override
        public List<Completion> getCompletionsAt(JTextComponent jtc, Point point) {
            return Collections.emptyList();
        }

        @Override
        public List<ParameterizedCompletion> getParameterizedCompletions(JTextComponent jtc) {
            return Collections.emptyList();
        }
    }
    
    private TSCompletion createTSCompletion(CompletionInfo.Entry source, CompletionProvider provider, String fileName, int position, String prefix) {
        TSCompletion comp = null;

        if (source.insertText == null) {
            if (source.name != null && source.name.startsWith(prefix)) {
                comp = new TSCompletion();
            }
        } else if (source.hasAction) {
            comp = new TSCompletion();
            System.out.println(comp.toString());
        } else {
            System.out.println("other?");
        }
        
        // fill in common basic values --- avoids need for long constructor
        if (comp != null) {
            comp.provider = provider;
            comp.source = source;
            comp.fileName = fileName;
            comp.position = position;
            comp.prefix = prefix;
        }
        
        return comp;
    }
    
    
    /**
     * Base class for TypeScript completions.
     */
    private class TSCompletion implements Completion, IconProvider, SecondaryTextProvider {

        private CompletionProvider provider;
        private CompletionInfo.Entry source;
        private String fileName;
        private int position;
        private String prefix;

        public TSCompletion() {
        }

        @Override
        public int compareTo(Completion rhs) {
            // completions are already sorted
            return 0;
        }

        @Override
        public String getAlreadyEntered(JTextComponent jtc) {
            return prefix;
        }

        @Override
        public Icon getIcon() {
            Icon icon;
            boolean isStatic = source.kindModifiers != null && source.kindModifiers.contains("static");
            switch (source.kind) {
                case "keyword":
                    icon = ICON_KEYWORD;
                    break;
                case "module":
                case "external module name":
                    icon = ICON_MODULE;
                    break;                    
                case "class":
                case "local class":
                    icon = ICON_CLASS;
                    break;
                case "interface":
                    icon = ICON_INTERFACE;
                    break;
                case "enum":
                    icon = ICON_ENUM;
                    break;                    
                case "enum member":
                    icon = ICON_ENUM_MEMBER;
                    break;
                case "var":
                case "local var":
                    icon = ICON_VAR;
                    break;
                case "let":
                    icon = ICON_LET;
                    break;
                case "const":
                    icon = ICON_CONST;
                    break;
                case "function":
                case "local function":
                    icon = ICON_FUNCTION;
                    break;
                case "method":
                case "constructor":
                    icon = ICON_METHOD;
                    break;
                case "getter":
                    icon = ICON_GETTER;
                    break;
                case "setter":
                    icon = ICON_SETTER;
                    break;
                case "property":
                    icon = ICON_PROPERTY;
                    break;
                case "type":
                    icon = ICON_TYPE;
                    break;
                case "alias":
                    icon = ICON_ALIAS;
                    break;
                case "primitive type":
                    icon = ICON_PRIMITIVE;
                    break;
                case "call":
                case "construct":
                    icon = ICON_CALL;
                    break;                    
                case "index":
                    icon = ICON_INDEX;
                    break;                    
                case "parameter":
                    icon = ICON_PARAMETER;
                    break;
                case "type parameter":
                    icon = ICON_TYPE_PARAMETER;
                    break;
                case "label":
                    icon = ICON_LABEL;
                    break;
                case "directory":
                    icon = ICON_DIRECTORY;
                    break;
                // "JSX attribute", "string", "link", "link name", "link text"
                default:
                    icon = ICON_NONE;
                    break;
            }
            return icon;
        }

        @Override
        public String getInputText() {
            return "";
        }

        @Override
        public CompletionProvider getProvider() {
            return provider;
        }

        @Override
        public int getRelevance() {
            return 0;
        }

        @Override
        public String getReplacementText() {
            return source.getTextToInsert();
        }

        @Override
        public String getSummary() {
            System.out.println(source);
            if (root != null) {
                CompletionInfo.EntryDetails details = root.getCodeCompletionDetails(fileName, position, source);
                if (details != null) {
                    if (details.actions != null) {
                        for (int i=0; i<details.actions.size(); ++i) {
                            System.out.println("ACTION #" + (i+1));
                            CodeAction action = details.actions.get(i);
                            System.out.println(action.description);
                            for (FileTextChanges changes : action.changes) {
                                System.out.println(changes);
                            }
                        }
                    }
                    
                    if (markdown == null) {
                        markdown = new MarkdownTransformer();
                    }
                    return markdown.render(
                      "```\n" + details.display + "\n```\n\n" + details.documentation
                    );
//                    return details.display + "<p>" + details.documentation + "<p>" + details.source;
                }
            }
            return null;
        }

        @Override
        public String getToolTipText() {
            return null;
        }
        
        @Override
        public String getText() {
            return source.name;
        }
        
        @Override
        public String getSecondaryText() {
            return source.kind;    
        }        

        @Override
        public String toString() {
            String s = source.name + " — ";
            if (source.kindModifiers != null && !source.kindModifiers.isEmpty()) {
                s += source.kindModifiers + ' ' + source.kind;
            } else {
                s += source.kind;
            }
            return s;
        }
    }
    
    private MarkdownTransformer markdown;
}
