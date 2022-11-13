import React from 'react';
import Navbar from './Navbar.jsx'
import { useSearchParams } from 'react-router-dom';

export default function Register(props) {
    const [searchParams] = useSearchParams();

    let errorMessage = null;

    if (searchParams.has("errorMsg"))
        errorMessage = searchParams.get("errorMsg");

    return (
        <div className="h-screen overflow-hidden">
            <Navbar />
            <div className="w-full h-full bg-body flex">
                <div className="w-5/12 bg-navbar m-auto mt-10 rounded-[30px] flex flex-wrap justify-center text-center pb-6">
                    <form method='post' className="w-4/6 text-white font-semibold" action='/register'>
                        <h3 className='font-bold text-3xl mt-8 mb-6'>Register</h3>
                        <div className='form-group mt-3'>
                            <p className="text-start">Username</p>
                            <input name='username' className='w-full bg-body rounded-[4px] h-10 pl-2' />
                        </div>
                        <br />
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
                    {
                        searchParams != null &&
                        <div className="w-full">
                            {errorMessage}
                        </div>
                    }
                </div>
            </div>
        </div>
    )
};
