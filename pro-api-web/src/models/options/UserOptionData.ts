import { computed } from "vue";
import { SelectOptionData } from "@arco-design/web-vue";

export const userRoleOptionData = computed<SelectOptionData>(() => [
  {
    label: "超管",
    value: "super",
  },
  {
    label: "管理员",
    value: "admin",
  },
  {
    label: "用户",
    value: "user",
  },
  {
    label: "封禁用户",
    value: "ban",
  },
]);

export const genderOptionData = computed<SelectOptionData>(() => [
  {
    label: "女",
    value: 0,
  },
  {
    label: "男",
    value: 1,
  },
  {
    label: "未知",
    value: undefined,
  },
]);
