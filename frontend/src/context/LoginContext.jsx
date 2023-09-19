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
            })
            .then((res) => res?.data)
            .then((payload) => {
                const loginData = payload?.data;
                if (loginData !== null && loginData !== undefined)
                setLoginState(loginData);
            });
    }, []);

    return (
        <LoginContext.Provider value={{loginState, setLoginState}}>
            {children}
        </LoginContext.Provider>
    );
}
