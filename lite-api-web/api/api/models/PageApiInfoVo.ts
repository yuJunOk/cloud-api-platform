/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiInfoVo } from './ApiInfoVo';
import type { OrderItem } from './OrderItem';
export type PageApiInfoVo = {
    records?: Array<ApiInfoVo>;
    total?: number;
    size?: number;
    current?: number;
    orders?: Array<OrderItem>;
    optimizeCountSql?: PageApiInfoVo;
    searchCount?: PageApiInfoVo;
    optimizeJoinOfCountSql?: boolean;
    maxLimit?: number;
    countId?: string;
    /**
     * @deprecated
     */
    pages?: number;
};

