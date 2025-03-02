import {useCallback} from 'react';
import {QueryFunctionContext, useMutation, useQuery, useQueryClient} from 'react-query';
import noop from 'lodash-es/noop';
//import { REQUEST_ERROR } from '@/hooks/useAPI/Templates.ts';
import {
  callbackParams,
  QueryKey,
  ReturnedCallback,
  UseAPIProps,
  UseAPIReturn,
  useQueryFetcherParams,
} from '@/utils/useAPI/Types';
import {serverApiInstance} from "@/utils/useAPI/serverApiInstance";
import {useSession} from "next-auth/react";

export default function useAPI<T = any>({
  APIController,
  isCallback = false,
  APIMethod,
  requestMethod = 'get',
  //errorTemplate = REQUEST_ERROR,
  requestBody,
  authRequired=true,
  requestQueryParams,
}: UseAPIProps): UseAPIReturn<T> {
  const url = `${APIController}/${APIMethod}`;

  const { data: session }:any = useSession();

  const queryClient = useQueryClient();

  const fetchAPI = useCallback(
    async ({ requestBody, requestQueryParams, requestMethod }: useQueryFetcherParams) => {
      try {
        return await serverApiInstance<T>({
          data: requestBody,
          method: requestMethod,
          params: requestQueryParams,
          url: url,
          accessToken:session?.accessToken
        });
      } catch (err: any) {
        //TODO Добавить вызов уведомления об ошибке через notification
        //console.log(errorTemplate);
        throw new Error(err.message);
      }
    },
    [session, url]
  );

  const queryFetcher = useCallback(
    async ({ queryKey }: QueryFunctionContext<[string, useQueryFetcherParams]>) => {
      const [, { requestBody, requestQueryParams, requestMethod }] = queryKey;
      return await fetchAPI({ requestBody, requestQueryParams, requestMethod });
    },
    [fetchAPI]
  );

  const mutationFetcher = useCallback(
    async ({ requestBody, requestQueryParams }: callbackParams) => {
      const response = await fetchAPI({ requestBody, requestQueryParams, requestMethod });

      return { response, requestQueryParams, requestMethod };
    },
    [fetchAPI, requestMethod]
  );

  const { isLoading, error, data, refetch } = useQuery<T, Error, T, QueryKey>({
    queryKey: [url, { requestBody, requestQueryParams, requestMethod }],
    queryFn: queryFetcher,
    enabled: !isCallback && (authRequired == false || (authRequired == true && session !== undefined))
  });

  const { mutateAsync } = useMutation({
    mutationFn: mutationFetcher,

    onSuccess: ({ requestQueryParams, requestMethod }) => {
      queryClient
        .invalidateQueries({
          queryKey: [url, { requestBody, requestQueryParams, requestMethod }],
        })
        .catch(noop);
    },
  });

  const callback = useCallback<ReturnedCallback>(
    async (callbackParams) => {
        if(authRequired==false || (authRequired==true && session!=undefined)){
            const requestBody=callbackParams?.requestBody
            const requestQueryParams=callbackParams?.requestQueryParams
            const { response } = await mutateAsync({ requestBody, requestQueryParams });
            return response;
        }
        return new Promise(noop);
    },
    [authRequired, mutateAsync, session]
  );

  return [{ data, isLoading, error: error ? error : undefined, refetch }, callback];
}
