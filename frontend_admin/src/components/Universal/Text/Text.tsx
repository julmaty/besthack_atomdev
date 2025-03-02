import type {ReactNode} from "react";

interface TextProps {
    children: ReactNode;
    fontSize?:number
    className?:string;
    style?:any;
}
const Text=({children, fontSize, className, style}:TextProps)=>{
    return(<div style={{fontSize:fontSize, ...style}} className={className}>
        {children}
    </div>)
}

export default Text;



