import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function YoutubeVideoStats(props) {
    let [videoId, setVideoId] = useState("");
    let [stats, setStats] = useState(null);
    let inputRef = useRef(null);

    function truncate(str, n){
        return (str.length > n) ? str.slice(0, n - 1) + '...' : str;
    }

    function urlToVideoId(url) {
        if (url.includes("youtu.be"))
            return url.split("youtu.be/")[1];
        else if (url.includes("youtube.com")) {
            const params = new URLSearchParams(url.split("?")[1]);
            return params.has("v") ? params.get("v") : "";
        } else
            return url;
    }

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let value = urlToVideoId(inputRef.current.value);

            function updateStats(videoId) {
                fetch("/api/youtube/stats?videoId=" + videoId).then(response => {
                    response.json().then(data => {
                        if ("error" in data) {
                            setStats(null);
                            return;
                        }
                        setStats(data);
                    });
                });
            }

            if (value != videoId) {
                setVideoId(value);

                if (!value || value.length === 0) {
                    setStats(null);
                    return;
                }

                updateStats(value);
            } else {
                updateStats(videoId);
            }
        }, 3000);
        return () => clearInterval(inteval);
    }, [videoId]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Youtube Stats</p>
            <input ref={inputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Enter a youtube video" type="text"></input>
            <br />
            <p className="font-bold text-gray-200">{stats != null ? truncate(stats.title, 80) : "Unknown video"}</p>
            <p className="font-bold text-gray-400">{stats != null ? stats.views + " views" : ""}</p>
            <p className="font-bold text-gray-400">{stats != null ? stats.likes + " likes" : ""}</p>
            <p className="font-bold text-gray-400">{stats != null ? stats.comments + " comments" : ""}</p>
            <div className="w-full grid justify-items-center">
                {
                    (stats != null && stats.thumbnail != null) &&
                    <img src={stats.thumbnail} />
                }
            </div>
        </div>
    )
}
