const pancing = require('../model/Pancing.js')
const response = require('../config/response')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId
exports.inputDataPancing = (data, gambar) =>
    new Promise(async (resolve, reject) =>{

        const pancingBaru = new pancing({
            kodePancing : data.kodePancing,
            merekPancing : data.merekPancing,
            tipePancing: data.tipePancing,
            ukuranPancing: data.ukuranPancing,
            warnaPancing: data.warnaPancing,
            hargaPancing: data.hargaPancing,
            gambar: gambar
        })

        await pancing.findOne({kodePancing: data.kodePancing})
            .then(pancing =>{
                if (pancing){
                    reject(response.commonErrorMsg('Kode pancing Sudah Digunakan'))
                }else{
                    pancingBaru.save()
                        .then(r =>{
                            resolve(response.commonSuccessMsg('Berhasil Menginput Data'))
                        }).catch(err =>{
                        reject(response.commonErrorMsg('Mohon Maaf Input pancing Gagal'))
                    })
                }
            }).catch(err =>{
            reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
        })
    })

exports.lihatDataPancing = () =>
    new Promise(async (resolve, reject) =>{
        await pancing.find({})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.lihatDetailDataPancing = (kodepancing) =>
    new Promise(async (resolve, reject) =>{
        await pancing.findOne({kodePancing: kodePancing})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.updatePancing = (id, data, gambar) =>
    new Promise(async (resolve, reject)=>{
        await pancing.updateOne(
            {_id : ObjectId(id)},
            {
                $set: {
                    kodePancing : data.kodePancing,
                    merekPancing : data.merekPancing,
                    tipePancing: data.tipePancing,
                    ukuranPancing: data.ukuranPancing,
                    warnaPancing: data.warnaPancing,
                    hargaPancing: data.hargaPancing,
                    gambar: gambar
                }
            }
        ).then(pancing =>{
            resolve(response.commonSuccessMsg('Berhasil Mengubah Data'))
        }).catch(err =>{
            reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
        })
    })

exports.hapuspancing = (_id) =>
    new Promise(async (resolve, reject) =>{
        await pancing.remove({_id: ObjectId(_id)})
            .then(() =>{
                resolve(response.commonSuccessMsg('Berhasil Menghapus Data'))
            }).catch(() =>{
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
            })
    })