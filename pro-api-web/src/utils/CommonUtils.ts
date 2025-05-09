/* eslint-disable */
import { SelectOptionData } from "@arco-design/web-vue";

export const findOptionsLabelByValue = (
  option: SelectOptionData,
  value: any
) => {
  return (option || []).find((item: any) => item.value === value)?.label ?? "";
};
