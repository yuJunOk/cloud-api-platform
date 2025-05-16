/**
 * 将 Content-Type 转换为代码编辑器支持的语言格式
 * @param contentType 如 "text/html;charset=UTF-8"
 * @returns 编辑器语言标识符 (如 html/json/xml)
 */
export function getEditorFormat(contentType: string | undefined): string {
  // 处理空值或非法输入
  if (!contentType) return "plaintext";

  // 提取主类型并标准化（移除参数/空格/小写化）
  const mimeType = contentType.split(";")[0].trim().toLowerCase();

  // MIME 类型到编辑器格式的映射
  const mimeToFormat: Record<string, string> = {
    "application/json": "json",
    "text/html": "html",
    "text/xml": "xml",
    "application/xml": "xml",
    "application/javascript": "javascript",
    "text/css": "css",
    "text/markdown": "markdown",
    "application/yaml": "yaml",
    "text/plain": "plaintext",
  };

  // 返回匹配的格式或默认值
  return mimeToFormat[mimeType] || "plaintext";
}
