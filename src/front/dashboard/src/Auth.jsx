import React from 'react';

export default function (props) {
    return (
        <div className="Auth-form-container">
            <form className="Auth-form">
                <div className='Auth-form-content'>
                    <h3 className='Auth-form-title'>Log In</h3>

                    <div className='form-group mt-3'>
                        <label>Email</label>
                        <input type="email" name='username' className='form-control mt-1' placeholder='abc@xyz.com' />
                    </div>

                    <div className='form-group mt-3'>
                        <label>Password</label>
                        <input type="password" name='password' className='form-control mt-1' />
                    </div>

                    <div className='d-grid gap-2 mt-3'>
                        <button type='submit' className='btn btn-primary'>Submit</button>
                    </div>
                </div>
            </form>
            <div className='Auth-oauth-content'>
                <a>Google</a>
            </div>
        </div>
    )
};
