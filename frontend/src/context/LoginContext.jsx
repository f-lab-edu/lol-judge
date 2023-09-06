import {createContext, useEffect, useState} from "react";
import {defaultLoginState} from "../utils/defaultData";
import axios from "axios";
import {convertUrl} from "../utils/urlUtil";

export const LoginContext = createContext();

export function LoginProvider({children}) {
    const [loginState, setLoginState] = useState(defaultLoginState);

    useEffect(() => {
        const url = convertUrl("/login");
        axios
            .get(url, {withCredentials: true})
            .catch(() => {
                setLoginState(defaultLoginState);
                alert("잘못된 접근입니다.");
            })
            .then((res) => res?.data)
            .then((payload) => {
                const lolLoginId = payload?.data;
                if (lolLoginId !== null && lolLoginId !== undefined)
                setLoginState({lolLoginId});
            });
    }, []);

    return (
        <LoginContext.Provider value={{loginState, setLoginState}}>
            {children}
        </LoginContext.Provider>
    );
}
