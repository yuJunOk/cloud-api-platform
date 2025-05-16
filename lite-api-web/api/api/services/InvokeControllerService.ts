/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiDebugDto } from '../models/ApiDebugDto';
import type { ResponseEntityApiResponseVo } from '../models/ResponseEntityApiResponseVo';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class InvokeControllerService {
    /**
     * @param requestBody
     * @returns ResponseEntityApiResponseVo OK
     * @throws ApiError
     */
    public static debug(
        requestBody: ApiDebugDto,
    ): CancelablePromise<ResponseEntityApiResponseVo> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/invoke/debug',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
