package com.discount.app.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.Menu
import com.discount.R
import java.io.IOException
import java.nio.charset.Charset


/**
 * Created by Avinash Kumar on 14/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class MyUtils {

    companion object {
        fun addFontToNavDrawer(m: Menu) {
            for (i in 0 until m.size()) {
                val mi = m.getItem(i)

                //for applying a font to subMenu ...
                val subMenu = mi.subMenu
                if (subMenu != null && subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        val s = SpannableString(subMenuItem.title)
                        s.setSpan(
                            TypefaceSpan("font/poppins_light.ttf"), 0, s.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        subMenuItem.title = s
                    }
                }

            }
        }

        val countryFlags = intArrayOf(
            R.drawable.flag_af,
            R.drawable.flag_al,
            R.drawable.flag_dz,
            R.drawable.flag_as_,
            R.drawable.flag_ad,
            R.drawable.flag_ao,
            R.drawable.flag_ai,
            R.drawable.flag_aq,
            R.drawable.flag_ag,
            R.drawable.flag_ar,
            R.drawable.flag_am,
            R.drawable.flag_aw,
            R.drawable.flag_au,
            R.drawable.flag_at,
            R.drawable.flag_az,
            R.drawable.flag_bs,
            R.drawable.flag_bh,
            R.drawable.flag_bd,
            R.drawable.flag_bb,
            R.drawable.flag_by,
            R.drawable.flag_be,
            R.drawable.flag_bz,
            R.drawable.flag_bj,
            R.drawable.flag_bm,
            R.drawable.flag_bt,
            R.drawable.flag_bo,
            R.drawable.flag_ba,
            R.drawable.flag_bw,
            R.drawable.flag_br,
            R.drawable.flag_io,
            R.drawable.flag_vg,
            R.drawable.flag_bn,
            R.drawable.flag_bg,
            R.drawable.flag_bf,
            R.drawable.flag_bi,
            R.drawable.flag_kh,
            R.drawable.flag_cm,
            R.drawable.flag_ca,
            R.drawable.flag_cv,
            R.drawable.flag_ky,
            R.drawable.flag_cf,
            R.drawable.flag_td,
            R.drawable.flag_cl,
            R.drawable.flag_cn,
            R.drawable.flag_cx,
            R.drawable.flag_cc,
            R.drawable.flag_co,
            R.drawable.flag_km,
            R.drawable.flag_ck,
            R.drawable.flag_cr,
            R.drawable.flag_hr,
            R.drawable.flag_cu,
            R.drawable.flag_cw,
            R.drawable.flag_cy,
            R.drawable.flag_cz,
            R.drawable.flag_cd,
            R.drawable.flag_dk,
            R.drawable.flag_dj,
            R.drawable.flag_dm,
            R.drawable.flag_do_,
            R.drawable.flag_tl,
            R.drawable.flag_ec,
            R.drawable.flag_eg,
            R.drawable.flag_sv,
            R.drawable.flag_gq,
            R.drawable.flag_er,
            R.drawable.flag_ee,
            R.drawable.flag_et,
            R.drawable.flag_fk,
            R.drawable.flag_fo,
            R.drawable.flag_fj,
            R.drawable.flag_fi,
            R.drawable.flag_fr,
            R.drawable.flag_pf,
            R.drawable.flag_ga,
            R.drawable.flag_gm,
            R.drawable.flag_ge,
            R.drawable.flag_de,
            R.drawable.flag_gh,
            R.drawable.flag_gi,
            R.drawable.flag_gr,
            R.drawable.flag_gl,
            R.drawable.flag_gd,
            R.drawable.flag_gu,
            R.drawable.flag_gt,
            R.drawable.flag_gg,
            R.drawable.flag_gn,
            R.drawable.flag_gw,
            R.drawable.flag_gy,
            R.drawable.flag_ht,
            R.drawable.flag_hn,
            R.drawable.flag_hk,
            R.drawable.flag_hu,
            R.drawable.flag_is_,
            R.drawable.flag_in_,
            R.drawable.flag_id,
            R.drawable.flag_ir,
            R.drawable.flag_iq,
            R.drawable.flag_ie,
            R.drawable.flag_im,
            R.drawable.flag_il,
            R.drawable.flag_it,
            R.drawable.flag_ci,
            R.drawable.flag_jm,
            R.drawable.flag_jp,
            R.drawable.flag_je,
            R.drawable.flag_jo,
            R.drawable.flag_kz,
            R.drawable.flag_ke,
            R.drawable.flag_ki,
            R.drawable.flag_kosovo_xk,
            R.drawable.flag_kw,
            R.drawable.flag_kg,
            R.drawable.flag_la,
            R.drawable.flag_lv,
            R.drawable.flag_lb,
            R.drawable.flag_ls,
            R.drawable.flag_lr,
            R.drawable.flag_ly,
            R.drawable.flag_li,
            R.drawable.flag_lt,
            R.drawable.flag_lu,
            R.drawable.flag_mo,
            R.drawable.flag_mk,
            R.drawable.flag_mg,
            R.drawable.flag_mw,
            R.drawable.flag_my,
            R.drawable.flag_mv,
            R.drawable.flag_ml,
            R.drawable.flag_mt,
            R.drawable.flag_mh,
            R.drawable.flag_mr,
            R.drawable.flag_mu,
            R.drawable.flag_mx,
            R.drawable.flag_fm,
            R.drawable.flag_md,
            R.drawable.flag_mc,
            R.drawable.flag_mn,
            R.drawable.flag_me,
            R.drawable.flag_ms,
            R.drawable.flag_ma,
            R.drawable.flag_mz,
            R.drawable.flag_mm,
            R.drawable.flag_na,
            R.drawable.flag_nr,
            R.drawable.flag_np,
            R.drawable.flag_nl,
            R.drawable.flag_nz,
            R.drawable.flag_ni,
            R.drawable.flag_ne,
            R.drawable.flag_ng,
            R.drawable.flag_nu,
            R.drawable.flag_kp,
            R.drawable.flag_mp,
            R.drawable.flag_no,
            R.drawable.flag_om,
            R.drawable.flag_pk,
            R.drawable.flag_pw,
            R.drawable.flag_ps,
            R.drawable.flag_pa,
            R.drawable.flag_pg,
            R.drawable.flag_py,
            R.drawable.flag_pe,
            R.drawable.flag_ph,
            R.drawable.flag_pn,
            R.drawable.flag_pl,
            R.drawable.flag_pt,
            R.drawable.flag_pr,
            R.drawable.flag_qa,
            R.drawable.flag_cg,
            R.drawable.flag_ro,
            R.drawable.flag_ru,
            R.drawable.flag_rw,
            R.drawable.flag_kn,
            R.drawable.flag_lc,
            R.drawable.flag_vc,
            R.drawable.flag_ws,
            R.drawable.flag_sm,
            R.drawable.flag_st,
            R.drawable.flag_sa,
            R.drawable.flag_sn,
            R.drawable.flag_rs,
            R.drawable.flag_sc,
            R.drawable.flag_sl,
            R.drawable.flag_sg,
            R.drawable.flag_sx,
            R.drawable.flag_sk,
            R.drawable.flag_si,
            R.drawable.flag_sb,
            R.drawable.flag_so,
            R.drawable.flag_za,
            R.drawable.flag_kr,
            R.drawable.flag_ss,
            R.drawable.flag_es,
            R.drawable.flag_lk,
            R.drawable.flag_sd,
            R.drawable.flag_sr,
            R.drawable.flag_sz,
            R.drawable.flag_se,
            R.drawable.flag_ch,
            R.drawable.flag_sy,
            R.drawable.flag_tw,
            R.drawable.flag_tj,
            R.drawable.flag_tz,
            R.drawable.flag_th,
            R.drawable.flag_tg,
            R.drawable.flag_tk,
            R.drawable.flag_to,
            R.drawable.flag_tt,
            R.drawable.flag_tn,
            R.drawable.flag_tr,
            R.drawable.flag_tm,
            R.drawable.flag_tc,
            R.drawable.flag_tv,
            R.drawable.flag_vi,
            R.drawable.flag_ug,
            R.drawable.flag_ua,
            R.drawable.flag_ae,
            R.drawable.flag_uk,
            R.drawable.flag_us,
            R.drawable.flag_uy,
            R.drawable.flag_uz,
            R.drawable.flag_vu,
            R.drawable.flag_va,
            R.drawable.flag_ve,
            R.drawable.flag_vn,
            R.drawable.flag_ye,
            R.drawable.flag_zm,
            R.drawable.flag_zw
        )

        /**
         * @param context
         * @param filename like "country.json"
         * @return json string
         */
        fun loadJSONFromAsset(context: Context, filename: String): String? {
            var json: String? = null
            try {
                val ips = context.assets.open(filename)
                val size = ips.available()
                val buffer = ByteArray(size)
                ips.read(buffer)
                ips.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}