import React from 'react';
import Navbar from './Navbar.jsx'
import { Link, useSearchParams } from 'react-router-dom';
import googleLogo from '../image/google_logo.png'
import redditLogo from '../image/reddit_logo.png'
import { useRef } from 'react';

export default function Auth(props) {
    const [searchParams] = useSearchParams();
    const emailRef = useRef(null);
    const passwordRef = useRef(null);

    var errorMessage = "";

    if (searchParams.get("error")) {
        errorMessage =
            <div className="bg-red-600 text-white mt-4">
                Your Email and/or Password is incorrect
            </div>
    }

    return (
        <div className="h-screen overflow-hidden">
            <Navbar />
            <div className="w-full h-full bg-body flex">
                <div className="w-5/12 bg-navbar m-auto mt-10 rounded-[30px] flex flex-wrap justify-center text-center pb-6">
                    <form method='post' className="w-4/6 text-white font-semibold">
                        <h3 className='font-bold text-3xl mt-8 mb-6'>Log In</h3>
                        <div className='form-group mt-3'>
                            <p className="text-start">Email</p>
                            <input ref={emailRef} type="email" name='username' className='w-full bg-body rounded-[4px] h-10 pl-2' placeholder='abc@xyz.com' />
                        </div>
                        <br />
                        <div className='form-group mt-3'>
                            <p className="text-start">Password</p>
                            <input ref={passwordRef} type="password" name='password' className='w-full bg-body rounded-[4px] h-10 pl-2' />
                        </div>
                        <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-2">
                            Submit
                        </button>
                    </form>
                    <div className="w-full">
                        {errorMessage}
                    </div>
                    <p className="text-white w-full mb-4">________________________________________________</p>
                    <div className='w-7/12 bg-white hover:bg-gray-200 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'>
                        <img src={googleLogo} alt="google_logo" className="ml-1 object-scale-down w-2/12" />
                        <a href="/oauth2/authorization/google" className="font-bold text-gray-700 m-auto">Log in with Google</a>
                    </div>
                    <div className='w-7/12 bg-white hover:bg-gray-200 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'>
                        <img src={redditLogo} alt="reddit_logo" className="ml-1 object-scale-down w-2/12" />
                        <a href="/oauth2/authorization/reddit" className="font-bold text-gray-700 m-auto">Log in with Reddit</a>
                    </div>
                </div>
            </div>
        </div>
    )
};
