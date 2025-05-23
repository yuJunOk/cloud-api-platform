/* eslint-disable */
import { SelectOptionData } from "@arco-design/web-vue";
import { JsonApiDataRule } from "@/models/type/ApiType";

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
export const generateJsonByApiDataRules = (rules: JsonApiDataRule[]) => {
  const result = new Map();

  // 递归处理嵌套结构
  const processField = (rule: JsonApiDataRule) => {
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
    if (rule.value !== undefined) {
      defaultValue = rule.type === 'number' ?
        Number(rule.value) : String(rule.value);
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