package dev.rism.bookshop;

/**
 * Created by risha on 2/26/2016.
 */
public class Bookdb {
    int _id;
    String _bname;
    String _bcat;
    String _bauthor;
    String _bprice;
    public Bookdb()
    {

    }
    public Bookdb(int _id,String _bname,String _bcat,String _bauthor,String _bprice)
    {
        this._id=_id;
        this._bname=_bname;
        this._bcat=_bcat;
        this._bauthor=_bauthor;
        this._bprice=_bprice;
    }
    public Bookdb(String _bname,String _bcat,String _bauthor,String _bprice)
    {
        this._bname=_bname;
        this._bcat=_bcat;
        this._bauthor=_bauthor;
        this._bprice=_bprice;
    }
    public Bookdb(String _bname,String _bauthor,String _bprice)
    {
        this._bname=_bname;
        this._bauthor=_bauthor;
        this._bprice=_bprice;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_bname() {
        return _bname;
    }

    public void set_bname(String _bname) {
        this._bname = _bname;
    }

    public String get_bcat() {
        return _bcat;
    }

    public void set_bcat(String _bcat) {
        this._bcat = _bcat;
    }

    public String get_bauthor() {
        return _bauthor;
    }

    public void set_bauthor(String _bauthor) {
        this._bauthor = _bauthor;
    }

    public String get_bprice() {
        return _bprice;
    }

    public void set_bprice(String _bprice) {
        this._bprice = _bprice;
    }

    @Override
    public String toString() {
        return _bname;
    }
}
