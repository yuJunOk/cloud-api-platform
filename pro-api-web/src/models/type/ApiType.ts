export type JsonApiDataRule = {
  key: string;
  name?: string;
  type?: string;
  value?: string;
  description?: string;
  children?: JsonApiDataRule[];
};
