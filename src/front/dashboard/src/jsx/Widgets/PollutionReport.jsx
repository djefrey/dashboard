import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function PollutionReport(props) {
    let [city, setCity] = useState({input: "", name: ""});
    let [coords, setCoords] = useState({lat: 0, lon: 0})
    let [pollution, setPollution] = useState({index: "?", textColor: "#FFFFFF"})
    let cityInputRef = useRef(null);

    const pollutionColor = new Map();
    pollutionColor.set(1, "#27e546");
    pollutionColor.set(2, "#27e59a");
    pollutionColor.set(3, "#dae527");
    pollutionColor.set(4, "#e5a527");
    pollutionColor.set(5, "#e52727");

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let newCity = cityInputRef.current.value;

            let updateWeather = (lat, lon) => {
                fetch("/api/weather/pollution?lat=" + lat + "&lon=" + lon).then(response => {
                    response.json().then(data => {
                        if ("error" in data)
                            return;

                        let pollution = data.list[0];

                        setPollution({
                            index: "" + pollution.main.aqi,
                            textColor: pollutionColor.get(pollution.main.aqi),
                        });
                    });
                })
            };

            if (city.input !== newCity) {
                if (!newCity || newCity.length === 0) {
                    setCity({
                        input: "",
                        name: "",
                    });

                    setPollution({
                        index: "?",
                        textColor: "#FFFFFF",
                    });
                    return;
                }

                fetch("/api/weather/geolocation?city=" + newCity).then(response => {
                    response.json().then(data => {
                        console.log(data);

                        if ("error" in data)
                            return;

                        setCity({
                            input: newCity,
                            name: data.city + (data.state !== undefined ? ", " + data.state : "") + ", " + data.country,
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
        }, 1000);
        return () => clearInterval(inteval);
    }, [city, coords, props]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Pollution Report</p>
            <input ref={cityInputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Choose a city" type="text"></input>
            <p className="text-6xl font-semibold mt-2" style={{color: pollution.textColor }}>{pollution.index} / 5</p>
            <span className="font-bold text-gray-400">
                {city.name}
            </span>
        </div>
    )
}
