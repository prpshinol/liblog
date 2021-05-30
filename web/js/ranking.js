/**
 * Created by Administartor on 2016/11/22.
 */

$(function () {
    $('.rank_today').click(function () {
        $('.rank_today').addClass('click_on');
        $('.rank_week').removeClass('click_on');
        $('.rank_total').removeClass('click_on');
        $('.arrow_today').removeClass('hidden');
        $('.arrow_week').addClass('hidden');
        $('.arrow_total').addClass('hidden');
        $('.rank_list_title').html('今天');
    });
    $('.rank_week').click(function () {
        $('.rank_week').addClass('click_on');
        $('.rank_today').removeClass('click_on');
        $('.rank_total').removeClass('click_on');
        $('.arrow_week').removeClass('hidden');
        $('.arrow_today').addClass('hidden');
        $('.arrow_total').addClass('hidden');
        $('.rank_list_title').html('最近一周');
    });
    $('.rank_total').click(function () {
        $('.rank_total').addClass('click_on');
        $('.rank_week').removeClass('click_on');
        $('.rank_today').removeClass('click_on');
        $('.arrow_total').removeClass('hidden');
        $('.arrow_week').addClass('hidden');
        $('.arrow_today').addClass('hidden');
        $('.rank_list_title').html('总榜单');
    });
});