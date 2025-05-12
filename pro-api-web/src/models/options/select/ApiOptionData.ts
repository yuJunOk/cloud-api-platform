import { computed } from "vue";
import { SelectOptionData } from "@arco-design/web-vue";

export const basicDataTypeOptionData = computed<SelectOptionData>(() => [
  {
    label: "string",
    value: "string",
  },
  {
    label: "number",
    value: "number",
  },
  {
    label: "boolean",
    value: "boolean",
  },
  {
    label: "array",
    value: "array",
  },
]);

export const jsonDataTypeOptionData = computed<SelectOptionData>(() => [
  {
    label: "string",
    value: "string",
  },
  {
    label: "number",
    value: "number",
  },
  {
    label: "boolean",
    value: "boolean",
  },
  {
    label: "array",
    value: "array",
  },
  {
    label: "object",
    value: "object",
  },
]);

export const headerContentTypeOptionData = computed<SelectOptionData>(() => [
  {
    label: "application/x-www-form-urlencoded",
    value: "application/x-www-form-urlencoded",
  },
  {
    label: "application/json",
    value: "application/json",
  },
  {
    label: "multipart/form-data",
    value: "multipart/form-data",
  },
]);

export const requestMethodTypeOptionData = computed<SelectOptionData>(() => [
  {
    label: "GET",
    value: "GET",
  },
  {
    label: "POST",
    value: "POST",
  },
]);

export const apiStatusOptionData = computed<SelectOptionData>(() => [
  {
    label: "发布",
    value: 1,
  },
  {
    label: "下线",
    value: 0,
  },
]);
