package core.exception

/**
 * Created by zephyre on 4/21/15.
 */
abstract class HedyBaseException(val errorCode: Int, val errorMsg: String) extends Exception {
}
