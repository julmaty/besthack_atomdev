export interface Token {
    name: string;
    email: string;
    sub:string;
    accessToken: string;
    accessTokenExpiry: string;
    refreshToken: string;
    iat: number,
    exp: number,
    jti: string;
}

export interface User {
    name: 'null',
    email: 'test2@mail.ru',
    image: undefined
    data:{
        accessToken:string;
        //accessTokenExpiry:string;
        //refreshToken:string;
    }
}