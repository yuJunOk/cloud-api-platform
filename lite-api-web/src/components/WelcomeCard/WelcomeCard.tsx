import React from "react";

export const WelcomeCard: React.FC = () => {

    return (
        <div className={'relative mt-4 lg:mt-0 space-x-2 flex justify-center shrink-0'}>
            <div
                className="absolute w-full opacity-70 blur-[120px] top-[2.5rem]
          before:absolute before:content-[''] before:block before:w-[26rem] before:h-[26rem] before:max-w-full before:rounded-full
          before:bg-indigo-500 bg-opacity-100 before:top-[10.25rem] before:left-[4.25rem]
          after:absolute after:content-[''] after:block after:w-[26rem] after:h-[26rem] after:max-w-full after:rounded-full after:bg-teal-500
          after:opacity-100 after:lg:left-[12.5rem]"
            ></div>
            <div
                className={
                    'bg-white relative w-full lg:w-[520px] lg:min-w-[520px] rounded-2xl p-10 pr-8 transition duration-200 perspective-right hover:scale-105 shadow-xl'
                }
            >
                <div>
                    <h2 className="text-xl mb-5 font-bold sm:text-2xl txt-0">稳定运营</h2>
                    <span className="text-lg leading-8">
                  我们为您提供可持续高质量的服务，采用先进的技术架构，确保API服务的稳定性和高可用性。
                </span>
                    <div className="space-y-3 relative mt-6">
                        <div
                            className="absolute left-4 top-0 bottom-[3rem] border-l-[1px] border-dashed border-gray-300"></div>
                        <div className="flex items-start space-x-4">
                            <div className="flex-shrink-0 relative z-10">
                                <div
                                    className="w-8 h-8 bg-blue-500 text-white rounded-full flex items-center justify-center">
                                    <i className={'iconfont icon-duoyanghua text-lg'}></i>
                                </div>
                            </div>
                            <div className="ml-6">
                                <b className="text-lg font-semibold">多样化的接口选择</b>
                                <div className="semi-timeline-item-content-time">
                                    平台上提供丰富多样的接口供您选择，涵盖了各个领域的功能和服务，满足不同需求。
                                </div>
                            </div>
                        </div>
                        <div className="flex items-start space-x-4">
                            <div className="flex-shrink-0 relative z-10">
                                <div
                                    className="w-8 h-8 bg-teal-500 text-white rounded-full flex items-center justify-center">
                                    <i className={'iconfont icon-jiaobenkongzhitai text-lg'}></i>
                                </div>
                            </div>
                            <div className="ml-6">
                                <b className="text-lg font-semibold">在线调试功能</b>
                                <div className="semi-timeline-item-content-time">
                                    您可以在平台上进行接口在线调试，快速验证接口的功能和效果，节省开发调试的时间和工作量。
                                </div>
                            </div>
                        </div>
                        <div className="flex items-start space-x-4">
                            <div className="flex-shrink-0 relative z-10">
                                <div
                                    className="w-8 h-8 bg-purple-500 text-white rounded-full flex items-center justify-center">
                                    {/*<i className="fas fa-chart-line text-sm"></i>*/}
                                    <i className={'iconfont icon-gongju1 text-lg'}></i>
                                </div>
                            </div>
                            <div className="ml-6">
                                <b className="text-lg font-semibold">客户端SDK支持</b>
                                <div className="semi-timeline-item-content-time">
                                    为了方便开发者集成接口到自己的代码中，平台提供了客户端SDK，使调用接口变得更加简单和便捷。
                                </div>
                            </div>
                        </div>
                        <div className="flex items-start space-x-4">
                            <div className="flex-shrink-0 relative z-10">
                                <div
                                    className="w-8 h-8 bg-green-500 text-white rounded-full flex items-center justify-center">
                                    <i className="fas fa-smile text-sm"></i>
                                </div>
                            </div>
                            <div className="ml-6">
                                <b className="text-lg font-semibold">开发者文档和技术支持</b>
                                <div className="semi-timeline-item-content-time">
                                    平台提供了详细的开发者文档和技术支持，帮助开发者快速接入和发布接口，解决遇到的问题和困难。
                                </div>
                            </div>
                        </div>
                        <div className="flex items-start space-x-4">
                            <div className="flex-shrink-0 relative z-10">
                                <div
                                    className="w-8 h-8 bg-yellow-500 text-white rounded-full flex items-center justify-center">
                                    <i className="fas fa-shield-alt text-sm"></i>
                                </div>
                            </div>
                            <div className="ml-6">
                                <b className="text-lg font-semibold">稳定和安全</b>
                                <div className="semi-timeline-item-content-time">
                                    平台致力于提供稳定和安全的接口调用服务，采用了安全措施和技术手段，保障用户数据的安全性和隐私保护。
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};