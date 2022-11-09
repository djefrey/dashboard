import React from 'react';
import Navbar from './Navbar.jsx'
import { Link, useSearchParams } from 'react-router-dom';
import googleLogo from '../image/google_logo.png'

export default function Auth(props) {
    const [searchParams] = useSearchParams();
    var errorMessage = "";

    if (searchParams.get("error")) {
        errorMessage =
            <div className="bg-red-600 text-white mt-4">
                Your Email and/or Password is incorrect
            </div>
    }

    return (
        <div className="h-max overflow-hidden">
            <Navbar />
            <div className="w-full h-full bg-body flex">
                <div className="w-5/12 bg-navbar m-auto mt-10 rounded-[30px] flex flex-wrap justify-center text-center pb-6">
                    <form className="w-4/6 text-white font-semibold">
                        <h3 className='font-bold text-3xl mt-8 mb-6'>Log In</h3>
                        <div className='form-group mt-3'>
                            <p className="text-start">Email</p>
                            <input type="email" name='username' className='w-full bg-body rounded-[4px] h-10 pl-2' placeholder='abc@xyz.com' />
                        </div>
                        <br />
                        <div className='form-group mt-3'>
                            <p className="text-start">Password</p>
                            <input type="password" name='password' className='w-full bg-body rounded-[4px] h-10 pl-2' />
                        </div>
                    </form>
                    <div className="w-full">
                    {errorMessage}
                    <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-2">
                        Submit
                    </button>
                    </div>
                    <p className="text-white w-full mb-4">________________________________________________</p>
                    <div className='w-5/12 bg-white hover:bg-gray-200 py-2 px-4 rounded-[5px] mt-2 mb-2 flex'>
                        <img src={googleLogo} alt="google_logo" className="ml-1 object-scale-down w-2/12" />
                        <a href="/oauth2/authorization/google" className="font-bold text-gray-700 m-auto">Log in with google</a>
                    </div>
                </div>
            </div>
        </div>
    )
};
