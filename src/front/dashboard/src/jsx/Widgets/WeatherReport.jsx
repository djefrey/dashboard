export default function WeatherReport() {
    return (
        <div className="flex justify-center">
            <div className=" w-5/12 bg-body rounded-[30px] text-white ml-4 pb-4 pt-2">
                <p className="font-semibold text-xl mb-3">Weather report</p>
                <input className="bg-navbar rounded-[4px] pl-2 ml-2" disabled={true} placeholder="Choose a city" type="text"></input>
                <div className="flex justify-center -mb-4 -mt-2">
                    <img src="http://openweathermap.org/img/wn/01d@2x.png" alt=""></img>
                </div>
                <span className="font-bold text-gray-400">
                    clear sky
                </span>
                <p className="text-6xl font-semibold mt-2">25°C</p>
            </div>
        </div>
    )
}