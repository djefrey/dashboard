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
                            {
                                userInfos.email == null &&
                                <form method='post' action='/api/user/setmailpassword' className="w-4/6 text-white font-semibold">
                                    <h3 className='font-bold text-3xl mt-8 mb-6'>Set Logins</h3>
                                    <div className='form-group mt-3'>
                                        <p className="text-start">Email</p>
                                        <input type="email" name='email' className='w-full bg-body rounded-[4px] h-10 pl-2' placeholder='abc@xyz.com' />
                                    </div>
                                    <br />
                                    <div className='form-group mt-3'>
                                        <p className="text-start">Password</p>
                                        <input type="password" name='password' className='w-full bg-body rounded-[4px] h-10 pl-2' />
                                    </div>
                                    <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-2">
                                        Submit
                                    </button>
                                </form>
                            }
                            {
                                userInfos.email != null &&
                                <form method='post' action='/api/user/setpassword' className="w-4/6 text-white font-semibold">
                                    <h3 className='font-bold text-3xl mt-8 mb-6'>Set Password</h3>
                                    <div className='form-group mt-3'>
                                        <p className="text-start">Password</p>
                                        <input type="password" name='password' className='w-full bg-body rounded-[4px] h-10 pl-2' />
                                    </div>
                                    <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-2">
                                        Submit
                                    </button>
                                </form>
                            }

                            <p className="text-white w-full mb-4">________________________________________________</p>

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
