/* eslint-disable */
import { SelectOptionData } from "@arco-design/web-vue";
import { ResponseRule } from "@/models/type/ApiType";

export const findOptionsLabelByValue = (
  option: SelectOptionData,
  value: any
) => {
  return (option || []).find((item: any) => item.value === value)?.label ?? "";
};

/**
 *
 * @param rules
 */
export const generateJsonByApiDataRules = (rules: ResponseRule[]) => {
  const result = new Map();

  // 递归处理嵌套结构
  const processField = (rule: ResponseRule) => {
    let defaultValue;

    // 根据类型生成默认值
    switch(rule.type) {
      case 'number':
        defaultValue = 0;
        break;
      case 'string':
        defaultValue = "";
        break;
      case 'array':
        defaultValue = [];
        break;
      case 'object':
        defaultValue = rule.children ?
          generateJsonByApiDataRules(rule.children) : {};
        break;
      default:
        defaultValue = null;
    }

    // 应用示例值
    if (rule.example !== undefined) {
      defaultValue = rule.type === 'number' ?
        Number(rule.example) : String(rule.example);
    }

    return defaultValue;
  };

  // 遍历所有字段
  rules.forEach(rule => {
    if (rule.name !== undefined){
      result.set(rule["name"], processField(rule));
    }
  });
  return Object.fromEntries(result);
}