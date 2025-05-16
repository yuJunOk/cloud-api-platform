import MonacoEditor from '@monaco-editor/react'
import {useEffect, useRef, useState} from "react";

interface Props {
    value?: string | undefined;
    height?: number | undefined;
    language?: string | undefined;
}

export const CodeViewer = ({value, language, height}: Props) => {
    const editorRef = useRef<any>(null);

    const [formatFlag, setFormatFlag] = useState({value, language});

    useEffect(() => {
        setFormatFlag({value, language});
    }, [value, language]);

    useEffect(() => {
        if (!editorRef.current){
            return;
        }
        editorRef.current.updateOptions({ readOnly: false });
        editorRef.current.getAction('editor.action.formatDocument')?.run().finally(() => {
            editorRef.current.updateOptions({ readOnly: true });
        });
    }, [formatFlag]);

    return (
        <MonacoEditor
            language={language || "plaintext"}
            width="100%"
            value={value}
            height={height || 600}
            onMount={(editor) => {
                editorRef.current = editor
            }}
            options={{
                theme: 'vs', // 编辑器主题颜色
                folding: true, // 是否折叠
                foldingHighlight: true, // 折叠等高线
                foldingStrategy: 'indentation', // 折叠方式  auto | indentation
                showFoldingControls: 'mouseover', // 是否一直显示折叠 always | mouseover
                disableLayerHinting: true, // 等宽优化
                emptySelectionClipboard: false, // 空选择剪切板
                selectionClipboard: false, // 选择剪切板
                automaticLayout: true, // 自动布局
                codeLens: false, // 代码镜头
                scrollBeyondLastLine: false, // 滚动完最后一行后再滚动一屏幕
                colorDecorators: true, // 颜色装饰器
                accessibilitySupport: 'off', // 辅助功能支持  "auto" | "off" | "on"
                lineNumbers: 'on', // 行号 取值： "on" | "off" | "relative" | "interval" | function
                lineNumbersMinChars: 5, // 行号最小字符   number
                readOnly: true, //是否只读  取值 true | false
                minimap: { enabled: false }, //小地图
            }}
        />
    );
};