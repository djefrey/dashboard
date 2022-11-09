export default function YoutubeStatistics() {
    return (
        <div className="flex justify-center">
            <div className=" w-5/12 bg-body rounded-[30px] text-white ml-4 pb-4 pt-2">
                <p className="font-semibold text-xl mb-3">Youtube Statistics</p>
                <input className="bg-navbar rounded-[4px] pl-2 ml-2 h-8" disabled={true} placeholder="Choose a channel" type="text"></input>
            </div>
        </div>
    )
}