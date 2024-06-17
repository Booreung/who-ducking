$(document).ready(function() {
    // 모달 열기
    $("#openRejectModal").click(function() {
        $("#rejectModal").show();
    });

    // 모달 닫기
    $("#closeModal, #closeModalButton").click(function() {
        $("#rejectModal").hide();
    });

    // 모달 외부 클릭 시 닫기
    $(window).click(function(event) {
        if ($(event.target).is("#rejectModal")) {
            $("#rejectModal").hide();
        }
    });
});