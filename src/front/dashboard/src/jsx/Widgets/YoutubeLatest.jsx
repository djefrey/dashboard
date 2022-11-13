import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function YoutubeLatest(props) {
    let [channelName, setChannelName] = useState("");
    let [stats, setStats] = useState(null);
    let inputRef = useRef(null);

    function truncate(str, n){
        return (str.length > n) ? str.slice(0, n - 1) + '...' : str;
    }

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let value = inputRef.current.value;

            function updateStats(channelName) {
                fetch("/api/youtube/latest?channelName=" + channelName).then(response => {
                    response.json().then(data => {
                        if ("error" in data) {
                            setStats(null);
                            return;
                        }
                        setStats(data);
                    });
                });
            }

            if (value != channelName) {
                setChannelName(value);

                if (value === "") {
                    setStats(null);
                    return;
                }

                updateStats(value);
            } else {
                updateStats(channelName);
            }
        }, 3000);
        return () => clearInterval(inteval);
    }, [channelName]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Youtube Stats</p>
            <input ref={inputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Enter a channel name" type="text"></input>
            <br />
            <p className="font-bold text-gray-200">{stats != null ? truncate(stats.title, 80) : "Unknown channel"}</p>
            <p className="font-bold text-gray-400">
                {
                    stats != null
                        ? stats.views + " views / " + stats.likes + " likes / " + stats.comments + " comments"
                        : ""
                }
            </p>
            <div className="w-full grid justify-items-center">
                {
                    (stats != null && stats.thumbnail != null) &&
                    <img src={stats.thumbnail} />
                }
            </div>
        </div>
    )
}
