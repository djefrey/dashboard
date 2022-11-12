import React, { useEffect, useRef } from "react";
import { useState } from "react";

export default function RedditKarma(props) {
    let [user, setUser] = useState("");
    let [karma, setKarma] = useState(null);
    let inputRef = useRef(null);

    useEffect(() => {
        const inteval = setInterval(() => {
            if (props.preview)
                return;

            let value = inputRef.current.value;

            function updateKarma(user) {
                fetch("/api/reddit/karma?username=" + user).then(response => {
                    response.json().then(data => {
                        if ("error" in data) {
                            setKarma(null);
                            return;
                        }
                        setKarma(data);
                    });
                });
            }

            if (value != user) {
                setUser(value);

                if (value === "") {
                    setKarma(null);
                    return;
                }

                updateKarma(value);
            } else {
                updateKarma(user);
            }
        }, 1000);
        return () => clearInterval(inteval);
    }, [user]);

    return (
        <div>
            <p className="font-semibold text-xl mb-3">Reddit Karma</p>
            <label>
                <span className="font-bold text-gray-100">/u/</span>
                <input ref={inputRef} className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={props.preview === true} placeholder="Enter a user" type="text"></input>
            </label>
            <br />
            <p className="text-6xl font-semibold mt-2">{(karma != null ? karma.totalKarma : "?") + " Î"}</p>
        </div>
    )
}
