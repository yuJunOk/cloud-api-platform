export type RequestRule = {
  key: string;
  name?: string;
  type?: string;
  example?: string;
  must?: boolean;
  description?: string;
  children?: RequestRule[];
};

export type ResponseRule = {
  key: string;
  name?: string;
  type?: string;
  example?: string;
  description?: string;
  children?: ResponseRule[];
};