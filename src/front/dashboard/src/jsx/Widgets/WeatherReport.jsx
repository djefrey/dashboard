export default function WeatherReport() {
    return (
        <div>
            <p className="font-semibold text-xl mb-3">Weather report</p>
            <input className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={true} placeholder="Choose a city" type="text"></input>
            <div className="flex justify-center -mb-4 -mt-2">
                <img src="http://openweathermap.org/img/wn/01d@2x.png" alt=""></img>
            </div>
            <span className="font-bold text-gray-400">
                clear sky
            </span>
            <p className="text-6xl font-semibold mt-2">25°C</p>
        </div>
    )
}