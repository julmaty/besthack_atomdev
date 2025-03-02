import apiInstance from "@/utils/apiInstance";

export default async function loginUserInBackend(email:string, password:string){

    const response= await apiInstance({
        method:'POST',
        url:`auth/login`,
        data:{
            username:email,
            password,
        }
    });

    const {token}=response.data

    return {access_token:token};
}





