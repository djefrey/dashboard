import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function RedditTop(props) {
    let [subreddit, setSubreddit] = useState("");
    let [topInfo, setTopInfo] = useState(null);
    let inputRef = useRef(null);

    function truncate(str, n){
        return (str.length > n) ? str.slice(0, n - 1) + '...' : str;
    }

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let value = inputRef.current.value;

            function updateSubredditTop(subreddit) {
                fetch("/api/reddit/top?subreddit=" + subreddit).then(response => {
                    response.json().then(data => {
                        if ("error" in data) {
                            setTopInfo(null);
                            return;
                        }
                        setTopInfo(data);
                    });
                });
            }

            if (value != subreddit) {
                setSubreddit(value);

                if (value === "") {
                    setTopInfo(null);
                    return;
                }

                updateSubredditTop(value);
            } else {
                updateSubredditTop(subreddit);
            }
        }, 1000);
        return () => clearInterval(inteval);
    }, [subreddit]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Subreddit top</p>
            <label>
                <span className="font-bold text-gray-100">/r/</span>
                <input ref={inputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Enter a subreddit" type="text"></input>
            </label>
            <br />
            <br />
            <span className="font-bold text-gray-200">
                {topInfo != null ? truncate(topInfo.title, 80) : "No subreddit set"}
            </span>
            <br />
            <span className="font-bold text-gray-400">
                {topInfo != null && ("/u/" + topInfo.author)}
            </span>
            <br />
            <span className="font-bold text-gray-400">
                {topInfo != null && topInfo.ups + " Î"}
            </span>
            <br />
            <div className="w-full grid justify-items-center">
                {
                    (topInfo != null && topInfo.thumbnail != null) &&
                    <img src={topInfo.thumbnail} />
                }
            </div>
        </div>
    )
}
