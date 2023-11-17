# travelution-soap

## Deskripsi
travelution-soap adalah backend dari keseluruhan aplikasi travelution dengan protokol SOAP. Aplikasi ini dibuat dengan menggunakan Java dengan library Jakarta-WS. Aplikasi ini dibuat untuk memenuhi tugas besar mata kuliah Pemrograman Aplikasi Berbasis Web.

## Overview Feature
1. SOAP Protocol in 2022
2. Logging
3. Dockerized
4. Built with Maven

## Skema basis data
Basis data terdiri atas 2 tabel, yaitu tabel logging dan subscription

![ss](Screenshot 2023-11-17 105053.png)

## Daftar endpoint
SubscriptionApprovalReq, SubscriptionListReq, SubscriptionReq, ValidateSubscriptionReq

## Cara menjalankan aplikasi
1. Pastikan sudah terinstall NodeJS, MySQL, dan Docker
2. Clone repositori ini
3. Buat file `.env` pada root folder aplikasi mengikuti contoh .env.example
4. Lakukan `docker compose up --build` dan `docker compose up`

## Pembagian Tugas
1. Accept: 13521003
2. Reject: 13521008
3. Validate: 13521029
