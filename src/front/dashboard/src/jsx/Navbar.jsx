import logo from '../image/Logo.png'
import { Link } from 'react-router-dom';

export default function Navbar() {
    return (
        <div className="w-full h-16 bg-navbar text-center flex">
            <div className=" w-2/12 flex">
                <img src={logo} alt="Logo" className="h-full ml-1"/>
                <p className="text-xl m-auto ml-0 font-semibold text-white">Dashboard</p>
            </div>
            <div className="flex ml-6">
                <p className="text-gray-500 m-auto text-sm font-semibold">Homepage</p>
                <Link to="/login.html">Login</Link>
            </div>
        </div>
    );
}