import React from 'react';
import Navbar from './Navbar.jsx'
import googleLogo from '../image/google_logo.png'
import redditLogo from '../image/reddit_logo.png'
import { useState, useEffect } from 'react';

export default function Account(props) {
    let [userInfos, setUserInfos] = useState(null);

    useEffect(() => {
        fetch("/api/user/me").then(response => {
            response.json().then(data => {
                setUserInfos(data);
            }).catch(() => {});
        }).catch(() => {});
    }, []);

    function insertAccountLink(name, url, icon, field) {
        return <>
            {
                userInfos[field] == false &&
                <div className='w-7/12 bg-white hover:bg-gray-200 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'>
                    <img src={icon} alt={icon} className="ml-1 object-scale-down w-2/12" />
                    <a href={url} className="font-bold text-gray-700 m-auto">{"Log in with " + name}</a>
                </div>
            }
            {
                userInfos[field] == true &&
                <div className='w-7/12 bg-green-300 hover:bg-green-400 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'>
                    <img src={icon} alt={icon} className="ml-1 object-scale-down w-2/12" />
                    <a href={url} className="font-bold text-gray-700 m-auto">{"Refresh " + name + " token"}</a>
                </div>
            }
        </>
    }

    return (
        <div className="h-screen overflow-hidden">
            <Navbar />
            <div className="w-full h-full bg-body flex">
                <div className="w-5/12 bg-navbar m-auto mt-10 rounded-[30px] flex flex-wrap justify-center text-center pb-6">
                    {
                        userInfos != null &&
                        <>
                            {insertAccountLink("Google", "/oauth2/authorization/google", googleLogo, "hasGoogleAccount")}
                            {insertAccountLink("Reddit", "/oauth2/authorization/reddit", redditLogo, "hasRedditAccount")}

                            <p className="text-white w-full mb-4">________________________________________________</p>

                            <form className='w-7/12 bg-red-300 hover:bg-red-400 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'  action='/logout'  method='POST'>
                                <button type='submit' className="font-bold text-gray-700 m-auto">
                                    Logout
                                </button>
                            </form>
                        </>
                    }
                </div>
            </div>
        </div>
    )
};
