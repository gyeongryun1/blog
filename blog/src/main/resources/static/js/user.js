//let index = {
//        init:function(){
//            $("#btn-save").on("click",()=>{
//            this.save();
//            });
//        },
//
//        save: function(){
//            alert('user의 save함수 호출됨');
//            let data = {
//                    username: $("#username").val(),
//                    password: $("#password").val(),
//                    email: $("#email").val()
//            }
//            console.log(data);
//     // ajax호출시 default가 비동기 호출
//            $.ajax({
//            type: "POST",
//            url:"/api/user",
//            data:JSON,stringify(data),
//            contentType:"application/json; charset=utf-8",
//            dataType:"json" //
//            }).done(function(resp){
//                alert("회원가입이 완료되었습니다.");
//                location.href="/index";
//            }).fail(function(error){
//                alert(JSON.stringify(error));
//            });
//        }
//}
//index.init();