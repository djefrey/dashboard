import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function WeatherReport(props) {
    let [city, setCity] = useState({input: "", name: ""});
    let [coords, setCoords] = useState({lat: 0, lon: 0})
    let [weather, setWeather] = useState({icon: '01d', description: 'clear sky', temperature: '25'})
    let cityInputRef = useRef(null);

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let newCity = cityInputRef.current.value;

            let updateWeather = (lat, lon) => {
                fetch("/api/weather/current?lat=" + lat + "&lon=" + lon).then(response => {
                    response.json().then(data => {
                        let weather = data.weather[0];
                        console.log(data);

                        if ("error" in data)
                            return;

                        setWeather({
                            icon: weather.icon,
                            description: weather.descritpion,
                            temperature: data.main.temp,
                        })
                    });
                })
            };

            if (city.input !== newCity) {
                if (newCity === "") {
                    setCity({
                        input: "",
                        name: "",
                    })
                    return;
                }

                fetch("/api/weather/geolocation?city=" + newCity).then(response => {
                    response.json().then(data => {
                        console.log(data);

                        if ("error" in data)
                            return;

                        setCity({
                            input: newCity,
                            name: data.city + ", " + data.state + ", " + data.country,
                        });
                        setCoords({lat: data.lat, lon: data.lon});
                        updateWeather(data.lat, data.lon);
                    })
                });
            } else {
                if (city === "")
                    return;
                updateWeather(coords.lat, coords.lon);
            }
        }, 5000);
        return () => clearInterval(inteval);
    }, [city, coords, props]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Weather report</p>
            <input ref={cityInputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Choose a city" type="text"></input>
            <div className="flex justify-center -mb-4 -mt-2">
                <img src={"http://openweathermap.org/img/wn/" + weather.icon + "@2x.png"} alt=""></img>
            </div>
            <span className="font-bold text-gray-400">
                {weather.description}
            </span>
            <p className="text-6xl font-semibold mt-2">{weather.temperature}°C</p>
            <span className="font-bold text-gray-400">
                {city.name}
            </span>
        </div>
    )
}