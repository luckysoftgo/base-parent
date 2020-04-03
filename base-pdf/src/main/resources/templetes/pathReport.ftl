<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-cn" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>${companyName}</title>
    <style type="text/css">
        /*body,div,p{font-family: "Microsoft YaHei", "SimHei", "Arial"}*/
        p{margin: 0;padding: 0;}
        .page {
            margin: 2cm 1cm;
            }

        .first-page {
            font-weight: bold;
            font-size: 16px;
            }

        .info {
            line-height: 50px;
            }

        .title {
            text-align: center;
            font-size: 26px;
            margin-top: 40px;
            font-weight: 900;
            margin-bottom: 10px;
            }

        .office {
            text-align: center;
            font-size: 56px;
            margin-top: 360px;
            margin-bottom: 20px;
            font-weight:bold;


            }
        .bq-text{}
        .t_text{
            text-align: center;
            font-size: 18px;
            line-height: 34px;
            }
        .table_bottom{
            margin-top: 420px;
            text-align: center;
            }

        #basic {
            margin-top: 350px;
            }

        .first-title {
            text-align: left;
            font-size: 22px;
            margin-top: 50px;
            margin-bottom: 50px;
            }

        table {
            border-collapse: collapse;
            border-spacing: 0;
            border-left: 0.5px solid #c4c5be;
            border-top: 0.5px solid #c4c5be;
            }

        table tr td {
            border-right: 0.5px solid #c4c5be;
            border-bottom: 0.5px solid #c4c5be;
            text-align: center;
            }

        /* .info_table_size {
            border-collapse: collapse;
            border-spacing: 0;
            border-left: 2px solid #888;
            border-top: 2px solid #888;
        }

        .info_table_size .dead_tr th,
        .info_table_size .dead_tr td {
            border-right: 2px solid #888;
            border-bottom: 2px solid #888;
            text-align: center;
        } */
        .content {
            font-size: 18px;
            width: 620px;
            }

        .first-td {
            width: 80px;
            }

        .second-td {
            width: 320px;
            }

        .first_table_tr {
            height: 40px;
            }

        .first_table {
            width: 600px;
            table-layout:fixed;
            word-break:break-strict;
            }

        .one_name {
            width: 80px;
            }

        .grey_td {
            background-color: #EAEBE4;
            }

        .info_table_size {
            width: 100%;
            table-layout:fixed;
            word-break:break-strict;
            font-size: 14px;
            }

        .info_table_size .dead_tr td{
            padding: 10px 12px;
            font-size: 14px;
            text-align: left;
            }
        .info_table_size .dead_tr .bolder_word{
            text-align: center;
            }

        .info_table_size .live_tr .xuhao {
            padding: 14px 2px;
            width: 32px;
            }

        .col_title{
            padding: 14px 2px;
            }

        .col_title_bg{
            padding: 6px 0px;
            width:180px;
            }

        .col_title_year {
            width: 36px;
            }

        table tr .bolder_word {
            font-weight: 900;
            text-align: center;
            }

        .title_big {
            font-weight: 900;
            font-size: 20px;
            text-align: left;
            background: #758D30;
            color: #ffffff;
            padding: 6px 20px;
            width: auto;
            display: inline-block;
            }

        .title_small {
            width: 100%;
            font-weight: 900px;
            font-size: 16px;
            margin-top: 20px;
            margin-bottom: 12px;
            padding-left: 20px;
            text-align: left;
            }

        .force_paging {
            page-break-after: always;
            }

        .surround_div {
            width: 100%;
            }

        @page {
            size: 210mm 297mm;
            margin: 0.25in;
            -fs-flow-bottom: "footer";
            -fs-flow-left: "left";
            -fs-flow-right: "right";
            /* border: thin solid black; */
            padding: 1em;
            }

        @page :right { @bottom-center { content:counter(page)"/"counter(pages);

            }

            }
        @page :left { @bottom-center { content:counter(page)"/"counter(pages);

            }

            }
        #footer {
            font-weight: bolder;
            font-size: 100%;
            position: absolute;
            top: 0;
            left: 0;
            -fs-move-to-flow: "footer";
            }

        #pagenumber:before {
            content: counter(page);
            }

        #pagecount:before {
            content: counter(pages);
            }

        E::before {
            content: counter(title) ；
            }

        .title_big {
            counter-increment: counter_h1;
            counter-reset: counter_h2;
            }

        .title_big::before {
            content: counter(counter_h1) ".";
            }

        .title_small {
            counter-increment: counter_h2;
            }

        .title_small::before {
            content: counter(counter_h1) "." counter(counter_h2);
            }
        /* ol li{
            list-style:none;
        } */
        .contents {
            page-break-after: always;
            }
        .contents ul li{line-height: 28px;font-size: 14px;}
        .fengmian{
            page-break-after: always;
            }
        .none_type_div{
            width:600px;
            font-size:20px;
            padding-left: 50px;
            }
        .shuoming{
            width: 100%;
            /*page-break-after: always;*/
            /*height:1000px;*/
            }
        .shuoming_title{
            font-size: 35px;
            font-weight: 900;
            text-align: center;
            padding-top: 60px;
            }
        strong{font-weight: bolder;}
        .shuoming_content{
            margin-top:20px;
            }
        .shuoming_content p{
            text-indent: 0;
            margin: 0 40px;
            padding: 0 20px 16px 40px;
            line-height: 28px;
            text-align: left;
            font-size: 14px;
            }
        .signature{
            font-size: 14px;
            font-weight: 900;
            margin-top: 120px;
            padding-right: 65px;
            float: right;
            }
        .title{
            background:url('file:///${impagePath}one-top-bf.jpg') left -20px no-repeat;
            width:100%;
            background-size: 100% 100%;
            }
        /*.title1{*/
        /*	background:url('file:///${impagePath}contenginfo_01-bf.jpg') left -20px no-repeat;*/
        /*	width:100%;*/
        /*	background-size: 100% 100%;*/
        /*}*/
        .green_table1{width: 100%;table-layout:fixed;word-break:break-strict;}
        .green_table,.green_table tr td{border: 0;vertical-align: top;}
        .green_table1,.green_table1 tr td{border: 0;text-align: center;padding: 10px 12px;font-size: 14px;}
        .green_table1 tr th{border-bottom: 0.5px solid #ccc;background-color: #e9eae5;font-weight: 900;padding: 10px 12px;font-size: 14px;border-top: 0.5px solid #758D30;}
        .green_table1 tr td{border-bottom: 0.5px solid #ccc;}
    </style>
</head>

<body style="font-family: SimSun; margin: 0px; padding: 0px;" >
<div class="page" style="margin: 0px; padding: 0px;">
    <!-- 报告封面开始 -->
    <div style="background: url('file:///${impagePath}one-bown.jpg') bottom no-repeat;width:100%;background-size: 100%;">
        <div class="first-page" style="width:100%;background: url('file:///${impagePath}one-top-bf.jpg') left top no-repeat;">
            <div class="info"></div>
            <div style="background:#7C962D;float: left; font-size: 16px; padding:5px 15px; color:#ffffff;border-radius:8px;margin-top:320px;margin-left:200px;">${reportVerson}</div>
            <div class="office">企业信用报告</div>
            <div class="title" style="text-align: center;background: none;">${companyName}</div>
            <div class="t_text"><b>报告编号:</b><span>${reportNo}</span></div>
            <div class="t_text"><b>完成状态:</b><span>最终报告</span></div>
            <div class="t_text"><b>报告日期:</b><span>${(.now)?string('yyyy年MM月dd日')}</span></div>
        </div>
        <div class="fengmian">
            <div class="table_bottom" >
                <b>版权所有:</b><span>小猫钓鱼有限公司</span>
            </div>
        </div>
    </div>
    <!-- 报告封面结束 -->

    <div style="page-break-after: always;">
        <!-- 报告说明开始 -->
        <div>
            <img src="file:///${impagePath}contenginfo_01-bf.jpg" style="width: 100%;height: 60px;padding: 0;margin:0;"></img>
        </div>
        <div class="shuoming">
            <div class="shuoming_title"><strong>报告说明</strong></div>
            <div class="shuoming_content">
                <p>本报告由小猫钓鱼有限公司出具，依据截止报告时间企业征信系统内记录的数据库信息生成。</p>
                <p>本机构承诺：除部分基本信息由信息主体提交外，其他信息均依据《征信业管理条例》采集、整理、保存，并在汇总、加工、整合、展示的全过程中保持客观中立地位。</p>
                <p>本报告是小猫钓鱼有限公司依据合理的技术规范做出的独立判断，未因受信息主体和其他任何组织或个人的影响而改变。</p>
                <p>如无特别说明，本报告中的金额类数据项单位均为万元。</p>
                <p>本报告未经授权，任何机构和个人不得复制、转载、出售、发布；如引用、刊发，须注明出处，且不得歪曲和篡改。</p>
                <p>本报告仅向信息主体提供，不得作为金融机构的授信依据，请妥善保管。因保管不当造成信息泄露的，我公司不承担相关责任。</p>
                <p>信息主体有权对本报告中的内容提出异议。</p>
                <p>小猫钓鱼有限公司是在地球村备案的,备案信息请登录:<br />http://www.baidu.com 进行查询，业务咨询电话：<br />010-10086129</p>
            </div>
            <div class="signature">征信机构(签章):小猫钓鱼有限公司</div>
        </div>
        <!-- 报告说明结束 -->
    </div>

    <!-- 信用特征_信用报告摘要 -->

    <div>
        <img src="file:///${impagePath}contenginfo_01-bf.jpg" style="width: 100%;height: 60px;padding: 0;margin:0;"></img>
    </div>
    <!-- 栏目大标题开始 -->
    <h1 class="title_big">企业信用特征</h1>
    <!-- 栏目大标题结束 -->
    <!-- 小标题开始 -->
    <h1 class="title_small">信用报告摘要</h1>
    <!-- 小标题结束 -->
    <!-- 信用特征摘要开始 -->
    <div class="surround_div force_paging">
        <table class="info_table_size force_paging">
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">企业名称</td>
                <td class="bolder_word" colspan="3" style="text-align: left">${outline.name}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">续存年限</td>
                <#if outline.subsist??>
                    <td>${outline.subsist}年</td>
                <#else>
                    <td>暂无数据</td>
                </#if>
                <td class="one_name grey_td bolder_word">企业状态</td>
                <td>${outline.regstatus!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">信用积分</td>
                <td style="padding: 0;">
                    <img src="file:///${scoreImg}" id="echarts_main" style="width: 168px;"></img>
                </td>
                <td class="one_name grey_td bolder_word">信用能力</td>
                <td style="padding: 0;">
                    <img src="file:///${radarImg}" id="echarts_main" style="width: 168px;height: 180px;"></img>
                </td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">企业标签</td>
                <td colspan="3">${outline.tags!'暂无数据'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word" rowspan="2">近一年工商变更</td>
                <td>股权变更</td>
                <#if outline.stockright??>
                    <td colspan="2">${outline.stockright}次</td>
                <#else>
                    <td colspan="2">0次</td>
                </#if>
            </tr>
            <tr class="dead_tr">
                <td>主要人员变更</td>
                <td colspan="2">暂无数据</td>
            </tr>

            <tr class="dead_tr">
                <td rowspan="3" class="one_name grey_td bolder_word">商业信息</td>
                <td rowspan="3">知识产权</td>
                <td>专利</td>
                <#if outline.patentcount??>
                    <td>${outline.patentcount}项</td>
                <#else>
                    <td>0项</td>
                </#if>
            </tr>

            <tr class="dead_tr">
                <td>著作权</td>
                <#if outline.copyright??>
                    <td>${outline.copyright}项</td>
                <#else>
                    <td>0项</td>
                </#if>
            </tr>
            <tr class="dead_tr">
                <td>商标</td>
                <#if outline.trademark??>
                    <td>${outline.trademark}项</td>
                <#else>
                    <td>0项</td>
                </#if>
            </tr>

            <tr class="dead_tr">
                <td rowspan="2" class="one_name grey_td bolder_word">正面记录</td>
                <td>行政奖励</td>
                <td colspan="2">暂无数据</td>
            </tr>
            <tr class="dead_tr">
                <td>红名单奖励</td>
                <td colspan="2">暂无数据</td>
            </tr>
            <tr class="dead_tr">
                <td rowspan="2" class="one_name grey_td bolder_word">负面记录</td>
                <td>行政处罚</td>
                <#if outline.punish??>
                    <td colspan="2">${outline.punish}条</td>
                <#else>
                    <td colspan="2">暂无数据</td>
                </#if>
            </tr>
            <tr class="dead_tr">
                <td>黑名单记录</td>
                <td colspan="2">暂无数据</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">网站地址</td>
                <td colspan="3">${outline.website!'-'}</td>
            </tr>
        </table>
    </div>
    <!-- 信用特征摘要结束 -->


    <!-- 企业基本信息_基本信息 -->
    <div>
        <img src="file:///${impagePath}contenginfo_01-bf.jpg" style="width: 100%;height: 60px;padding: 0;margin:0;"></img>
    </div>
    <!-- 栏目大标题开始 -->
    <h1 class="title_big">企业基本信息</h1>
    <!-- 栏目大标题结束 -->
    <!-- 小标题开始 -->
    <h1 class="title_small">基本信息</h1>
    <!-- 小标题结束 -->
    <!-- 企业基本信息开始 -->
    <div class="surround_div force_paging">
        <table class="info_table_size">
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">企业名称</td>
                <td class="bolder_word" colspan="3" style="text-align: left">${baseInfo.name}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">法定代表人</td>
                <td>${baseInfo.legalPersonName!'-'}</td>
                <td class="one_name grey_td bolder_word">注册资本</td>
                <td>${baseInfo.regCapital!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">注册时间</td>
                <td>${baseInfo.estiblishTime!'-'}</td>
                <td class="one_name grey_td bolder_word">公司状态</td>
                <td>${baseInfo.regStatus!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">工商注册号</td>
                <td>${baseInfo.regNumber!'-'}</td>
                <td class="one_name grey_td bolder_word">组织机构代码</td>
                <td>${baseInfo.orgNumber!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">统一信用代码</td>
                <td>${baseInfo.creditCode!'-'}</td>
                <td class="one_name grey_td bolder_word">公司类型</td>
                <td>${baseInfo.companyOrgType!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">纳税人识别号</td>
                <td>${baseInfo.taxNumber!'-'}</td>
                <td class="one_name grey_td bolder_word">行业</td>
                <td>${baseInfo.industry!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">营业期限</td>
                <td>-</td>
                <td class="one_name grey_td bolder_word">核准日期</td>
                <td>${baseInfo.approvedTime!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">纳税人资质</td>
                <td>-</td>
                <td class="one_name grey_td bolder_word">人员规模</td>
                <td>${baseInfo.staffNumRange!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">实缴资本</td>
                <td>${baseInfo.actualCapital!'-'}</td>
                <td class="one_name grey_td bolder_word">登记机关</td>
                <td>${baseInfo.regInstitute!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">参保人数</td>
                <#if baseInfo.socialStaffNum??>
                    <td>${baseInfo.socialStaffNum}人</td>
                <#else>
                    <td>-</td>
                </#if>
                <td class="one_name grey_td bolder_word">英文名称</td>
                <#if baseInfo.property3??>
                    <td>${baseInfo.property3?replace("&","&amp;")}</td>
                <#else>
                    <td>-</td>
                </#if>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">注册地址</td>
                <td colspan="3">${baseInfo.regLocation!'-'}</td>
            </tr>
            <tr class="dead_tr">
                <td class="one_name grey_td bolder_word">经营范围</td>
                <td colspan="3">${baseInfo.businessScope!'-'}</td>
            </tr>
        </table>
    </div>
    <!-- 企业基本信息结束 -->

</div>

</body>

</html>
