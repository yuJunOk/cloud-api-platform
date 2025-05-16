export type JsonApiDataRule = {
  key: string;
  name?: string;
  type?: string;
  value?: string;
  must?: boolean;
  description?: string;
  children?: JsonApiDataRule[];
};
