/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EmailDto } from '../models/EmailDto';
import type { IdBatchDto } from '../models/IdBatchDto';
import type { IdDto } from '../models/IdDto';
import type { LoginByEmailDto } from '../models/LoginByEmailDto';
import type { ResetPwdByEmailDto } from '../models/ResetPwdByEmailDto';
import type { ResponseEntityBoolean } from '../models/ResponseEntityBoolean';
import type { ResponseEntityLoginUserBo } from '../models/ResponseEntityLoginUserBo';
import type { ResponseEntityLong } from '../models/ResponseEntityLong';
import type { ResponseEntityObject } from '../models/ResponseEntityObject';
import type { ResponseEntityUserDo } from '../models/ResponseEntityUserDo';
import type { UserDo } from '../models/UserDo';
import type { UserRegisterDto } from '../models/UserRegisterDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserControllerService {
    /**
     * @param requestBody
     * @returns ResponseEntityBoolean OK
     * @throws ApiError
     */
    public static update(
      requestBody: UserDo,
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
     * @returns ResponseEntityObject OK
     * @throws ApiError
     */
    public static sendResetPwdCaptcha(
      requestBody: EmailDto,
    ): CancelablePromise<ResponseEntityObject> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/sendResetPwdCaptcha',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityObject OK
     * @throws ApiError
     */
    public static sendRegisterCaptcha(
      requestBody: EmailDto,
    ): CancelablePromise<ResponseEntityObject> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/sendRegisterCaptcha',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityObject OK
     * @throws ApiError
     */
    public static resetPwdByEmail(
      requestBody: ResetPwdByEmailDto,
    ): CancelablePromise<ResponseEntityObject> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/resetPwdByEmail',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityLong OK
     * @throws ApiError
     */
    public static register(
      requestBody: UserRegisterDto,
    ): CancelablePromise<ResponseEntityLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/register',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param requestBody
     * @returns ResponseEntityLoginUserBo OK
     * @throws ApiError
     */
    public static login(
      requestBody: LoginByEmailDto,
    ): CancelablePromise<ResponseEntityLoginUserBo> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/login',
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
      requestBody: UserDo,
    ): CancelablePromise<ResponseEntityLong> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param id
     * @returns ResponseEntityUserDo OK
     * @throws ApiError
     */
    public static getById(
      id: number,
    ): CancelablePromise<ResponseEntityUserDo> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/getById',
            query: {
                'id': id,
            },
        });
    }
    /**
     * @returns ResponseEntityLoginUserBo OK
     * @throws ApiError
     */
    public static getCurrentUser(): CancelablePromise<ResponseEntityLoginUserBo> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/current',
        });
    }
}
