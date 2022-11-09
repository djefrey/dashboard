import logo from '../image/Logo.png'
import { Link } from 'react-router-dom';
import { CgProfile } from 'react-icons/cg'

export default function Navbar() {
    return (
        <div className="w-full h-16 bg-navbar text-center flex border-b-[1px] border-gray-200">
            <div className=" w-2/12 flex">
                <img src={logo} alt="Logo" className="h-full ml-1"/>
                <p className="text-xl m-auto ml-0 font-semibold text-white">Dashboard</p>
            </div>
            <div className="flex ml-20">
                <Link className="text-gray-500 m-auto font-semibold" to="/index.html" >Homepage</Link>
            </div>
            <div className="w-full m-auto flex justify-end mr-20">
                <Link to="/login.html" className="flex">
                    <CgProfile className="text-3xl mr-2 text-gray-200"/>
                    <p className="text-gray-500 text-lg font-semibold">Login</p>
                </Link>
            </div>
        </div>
    );
}