package com.example.hatice.project.ModelG;

/**
 * Created by gncal on 7/27/2017.
 */

/* Sayfalar arası taşınacak nesneler için serializable ya da parcelable sınıfları
kullanılır. Parcelable sınıfı serializable sınıfına göre çok daha hızlıdır.
Rehberden okunup davetli listesine atılacak nesneler için parcelable sınıfından
yararlanıyoruz.
 */
public class Contact {

    //name: kişi adı, phoneNumber: kayıtlı telefon numarası, label. o telefon numarasının türü (mobile, home, work vb.)
    private String id, name, phoneNumber, label;

    public Contact(String id, String name, String phoneNumber, String label) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.label = label;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
