/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddApiInfoDto } from '../models/AddApiInfoDto';
import type { IdBatchDto } from '../models/IdBatchDto';
import type { IdDto } from '../models/IdDto';
import type { QueryApiInfoPageDto } from '../models/QueryApiInfoPageDto';
import type { ResponseEntityApiInfoDo } from '../models/ResponseEntityApiInfoDo';
import type { ResponseEntityBoolean } from '../models/ResponseEntityBoolean';
import type { ResponseEntityListApiInfoVo } from '../models/ResponseEntityListApiInfoVo';
import type { ResponseEntityLong } from '../models/ResponseEntityLong';
import type { ResponseEntityPageApiInfoVo } from '../models/ResponseEntityPageApiInfoVo';
import type { UpdateApiInfoDto } from '../models/UpdateApiInfoDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ApiInfoControllerService {
    /**
     * @param requestBody
     * @returns ResponseEntityBoolean OK
     * @throws ApiError
     */
    public static update(
        requestBody: UpdateApiInfoDto,
    ): CancelablePromise<ResponseEntityBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/update',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityPageApiInfoVo OK
     * @throws ApiError
     */
    public static getByPage(
        requestBody: QueryApiInfoPageDto,
    ): CancelablePromise<ResponseEntityPageApiInfoVo> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityBoolean OK
     * @throws ApiError
     */
    public static delete(
        requestBody: IdDto,
    ): CancelablePromise<ResponseEntityBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/delete',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityBoolean OK
     * @throws ApiError
     */
    public static deleteBatch(
        requestBody: IdBatchDto,
    ): CancelablePromise<ResponseEntityBoolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/deleteBatch',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityLong OK
     * @throws ApiError
     */
    public static add(
        requestBody: AddApiInfoDto,
    ): CancelablePromise<ResponseEntityLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param searchText
     * @returns ResponseEntityListApiInfoVo OK
     * @throws ApiError
     */
    public static search(
        searchText: string,
    ): CancelablePromise<ResponseEntityListApiInfoVo> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/search',
            query: {
                'searchText': searchText,
            },
        });
    }
    /**
     * @param id
     * @returns ResponseEntityApiInfoDo OK
     * @throws ApiError
     */
    public static getById(
        id: number,
    ): CancelablePromise<ResponseEntityApiInfoDo> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/getById',
            query: {
                'id': id,
            },
        });
    }
}
