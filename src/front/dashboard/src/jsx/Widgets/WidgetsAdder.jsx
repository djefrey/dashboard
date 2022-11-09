import WeatherReport from './WeatherReport'
import Dropdown from 'react-dropdown';
import { useState } from 'react';

export default function WidgetsAdder({formHandler}) {
    const [currentWidget, setCurrentWidget] = useState("");
    const handleChange = event => {
        setCurrentWidget(event.target.data);
        console.log(currentWidget);
    }

    const options = [
        'Wheater Report', 'Youtube Statistics', 'Reddit '
    ];
    const defaultOption = options[0];

    return (
        <div className="absolute w-screen h-screen bg-black/50 z-10">
            <div className="w-3/6 h-min bg-navbar rounded-[10px] text-center m-auto mt-6">
                <div className="flex justify-end mr-10 pt-4">
                    <button className="text-3xl text-gray-600" onClick={() => formHandler()}>x</button>
                </div>
                <p className="text-2xl font-semibold text-white">Widget Creation Examples</p>
                <div className="w-full m-auto mt-2">
                    <p className="text-gray-500 font-semibold mt-3">Choose one widget</p>
                    <div className="flex justify-center mb-6 mt-3">
                        <Dropdown className="w-2/6" options={options} onChange={() => handleChange()} value={defaultOption} placeholder="Select an option" />
                    </div>
                    <WeatherReport />
                    <button type="button" onClick={() => formHandler()} className="bg-gray-700 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10">
                        Cancel
                    </button>
                    <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10 ml-4">
                        Submit
                    </button>
                </div>
            </div>
        </div>
    )
}