"use client"

import {Input, Form} from'antd'

interface TextFieldProps {
    name:string,
    status?:any,
    label?:string,
    errorText?:string,
    isError?:boolean,
    isPassword?:boolean,
    isRequired?:boolean,
    onChange?:(e: any) => void,
    onFocus?:(e: any) => void,
    onBlur?:(e: any) => void,
    fontWeight?:number,
    className?:string,
    isSearch?:boolean,
    placeholder?:string;
    onSearch?:any;
    size?:'small' | 'middle' | 'large';
}

const FormTextField=({
    name,
    status,
    label,
    errorText,
    isError,
    isPassword=false,
    isRequired=true,
    onChange,
    className,
    placeholder,
    isSearch,
    onFocus,
    onBlur,
    onSearch,
    fontWeight,
    size="large",
    }:TextFieldProps) => {
    return(
        <Form.Item
            style={{fontWeight:`${fontWeight}`, marginBottom: isError ? 25 : 5}}
            label={label}
            name={name}
            rules={[
                {
                    required: isRequired,
                    message: errorText,
                    type:'string',
                    validator(_: any, value: any) {
                        if(isError || (isRequired && !value)){
                            return Promise.reject(errorText);
                        }
                        return Promise.resolve();
                    }
                },
            ]}
        >
            {isPassword?
                <Input.Password size={size} className={className} status={status} onChange={onChange} onFocus={onFocus} onBlur={onBlur}/>
                :

                isSearch?

                <Input.Search  onSearch={onSearch} placeholder={placeholder} size={size} status={status} onChange={onChange} onFocus={onFocus} onBlur={onBlur} className={className}/>
                :
                <Input size={size} placeholder={placeholder} status={status} onChange={onChange} onFocus={onFocus} onBlur={onBlur} className={className}/>
            }

        </Form.Item>
    )
};

export default FormTextField;