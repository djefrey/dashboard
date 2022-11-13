import WeatherReport from './WeatherReport'
import Widget from './Widget'
import Dropdown from 'react-dropdown';
import { useState } from 'react';
import { useEffect } from 'react';
import PollutionReport from './PollutionReport';
import RedditTop from './RedditTop';
import RedditKarma from './RedditKarma';
import YoutubeVideoStats from './YoutubeVideoStats';
import YoutubeLatest from './YoutubeLatest';

export default function WidgetsAdder({ formHandler, formSubmit, widgetSelection }) {
    const [currentWidget, setCurrentWidget] = useState("Weather Report");
    const [display, setDisplay] = useState(<Widget widgetContent={<WeatherReport preview={true} />} noResize={true} />);
    const handleChange = event => {
        setCurrentWidget(event.value);
    }

    useEffect(() => {
        switch (currentWidget) {
            case "Weather Report":
                setDisplay(<Widget widgetContent={<WeatherReport preview={true} />} noResize={true} />);
                break;
            case "Pollution Report":
                setDisplay(<Widget widgetContent={<PollutionReport preview={true} />} noResize={true} />);
                break;
            case "Youtube Stats":
                setDisplay(<Widget widgetContent={<YoutubeVideoStats preview={true} />} noResize={true} />);
                break;
            case "Youtube Latest":
                setDisplay(<Widget widgetContent={<YoutubeLatest preview={true} />} noResize={true} />);
                break;
            case "Reddit Karma":
                setDisplay(<Widget widgetContent={<RedditKarma preview={true} />} noResize={true} />);
                break;
            case "Reddit Top Post":
                setDisplay(<Widget widgetContent={<RedditTop preview={true} />} noResize={true} />);
                break;
            default:
                break;
        }
    }, [currentWidget])

    const defaultOption = widgetSelection[0];
    return (
        <div className="absolute w-screen h-screen bg-black/50 z-10">
            <div className="w-5/6 h-5/6 bg-body rounded-[10px] text-center m-auto mt-6">
                <div className="flex flex-x h-full">
                    <div className="w-5/12 h-full m-auto">
                        <div className="mt-40">
                            <p className="text-2xl font-semibold text-white">Widget Creation Examples</p>
                            <p className="text-gray-500 font-semibold mt-3">Choose one widget</p>
                            <div className="flex justify-center mb-6 mt-3">
                                <Dropdown className="w-3/6" options={widgetSelection} onChange={handleChange} value={defaultOption} placeholder="Select an option" />
                            </div>
                        </div>
                    </div>
                    <div className="w-7/12 h-full">
                        <div className="h-4/6 flex justify-center mt-10">
                            {display}
                        </div>
                        <button type="button" onClick={() => formHandler()} className="bg-gray-700 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10">
                            Cancel
                        </button>
                        <button type="submit" onClick={() => formSubmit(currentWidget)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-[5px] mt-10 mb-10 ml-4">
                            Submit
                        </button>
                    </div>
                </div>
                <div className="w-8/12 m-auto mt-2 ">
                </div>
            </div>
        </div>
    )
}