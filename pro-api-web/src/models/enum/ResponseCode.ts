/**
 * 响应码定义
 */
const RESPONSE_CODE = {
  /**
   * 操作成功
   */
  SUCCESS: 0,
  /**
   * 参数错误
   */
  PARAMS_ERROR: 40000,
  /**
   * 未登录
   */
  UNAUTHORIZED: 40100,
  /**
   * 操作无权限
   */
  FORBIDDEN: 40300,
  /**
   * 参数错误
   */
  NOT_FOUND: 40400,
  /**
   * 系统内部异常
   */
  SYSTEM_ERROR: 50000,
  /**
   * 操作失败
   */
  OPERATION_ERROR: 50001,
};

export default RESPONSE_CODE;
