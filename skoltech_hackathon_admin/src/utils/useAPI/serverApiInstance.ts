'use server'
import {serverApiInstanceProps} from "@/utils/useAPI/Types";
import apiInstance from "@/utils/apiInstance";

export async function serverApiInstance<T>({data,method,params,url, accessToken}:serverApiInstanceProps):Promise<T> {

   try{
    const res=await apiInstance<T>({
        data,
        method,
        params,
        url,
        headers:{
            authorization: `Bearer ${accessToken}`,
        }
    });

    console.log('res.data', res.data);

    return res.data;

   }catch(err){
       console.log('err', err);
       throw err;
   }
}









