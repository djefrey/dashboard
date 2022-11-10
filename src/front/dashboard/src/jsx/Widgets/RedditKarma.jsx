export default function RedditStatistics() {
    return (
        <div>
            <p className="font-semibold text-xl mb-3">Reddit Statistics</p>
            <input className="bg-body rounded-[4px] pl-2 ml-2 h-8" disabled={true} placeholder="Choose a subReddit" type="text"></input>
        </div>
    )
}